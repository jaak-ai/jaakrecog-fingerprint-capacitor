//
//  ApiClient.swift
//  Plugin
//
//  Created by Alex on 11/09/21.
//  Copyright © 2021 Max Lynch. All rights reserved.
//

import Foundation
import Combine

protocol ApiClientProtocol {
    typealias Headers = [String: Any]

//    func post<T, U>(url: URL,
//                 headers: Headers,
//                 payload: T?) -> AnyPublisher<U, Error> where T: Codable, U: Decodable
    func post<T, U>(url: URL, headers: Headers, payload: T?, completion: @escaping((Result<U, ApiError>) -> Void)) where T: Codable, U: Codable
}

final class ApiClient: ApiClientProtocol {
    
    func post<T, U>(url: URL, headers: Headers, payload: T?, completion: @escaping ((Result<U, ApiError>) -> Void)) where T : Codable, U : Codable {
        var request = URLRequest(url: url)
        print("Making request to URL: \(url.absoluteString)")
        request.httpMethod = "POST"
        
        headers.forEach { (key, value) in
            if let value = value as? String {
                request.setValue(value, forHTTPHeaderField: key)
            }
        }
        
        if let payload = payload {
            let encoder = JSONEncoder()
            do {
                let jsonData = try encoder.encode(payload)
                request.httpBody = jsonData
            }catch {
                print("\(error.localizedDescription)")
            }
        }
        
        call(with: request, completion: completion)
    }
    
    private func call<U>(with request: URLRequest, completion: @escaping((Result<U, ApiError>) -> Void)) where U : Codable {
        let dataTask = URLSession.shared.dataTask(with: request) { (data, response, error) in
            guard error == nil else {
                print("Error != nil -> \(error!)")
                completion(.failure(.serverError))
                return
            }
            
            do {
                guard let data = data else {
                    print("No data")
                    completion(.failure(.serverError))
                    return
                }
                
                print("Response data: \(data)")
                
                if let urlResponse = response as? HTTPURLResponse {
                    print("************* RESPONSE *************")
                    print("Status code: \(urlResponse.statusCode)")
                    print("Headers: \(urlResponse.allHeaderFields)")
                }
                
                let object = try JSONDecoder().decode(U.self, from: data)
                completion(.success(object))
            } catch {
                print("Invalid body, \(error)")
                completion(.failure(.invalidBody))
            }
        }
        
        dataTask.resume()
    }
}

struct Endpoint {
    var isProd: Bool = false
    var path: String
    var queryItems: [URLQueryItem] = []
    var token: String?
}

//https://dev.api.jaakrecog.com/api/session
extension Endpoint {
    var url: URL {
        
        var components = URLComponents()
        components.scheme = "https"
        components.host = isProd ? "api.jaakrecog.com" : "dev.api.jaakrecog.com"
        components.path = path
        components.queryItems = queryItems

        guard let url = components.url else {
            preconditionFailure("Invalid URL components: \(components)")
        }

        return url
    }

    var headers: [String: Any] {
        var returningHeaders: [String: Any] = [
            "Content-Type" : "application/json; charset=utf-8",
            "Accept" : "application/json"
        ]
        
        if let token = token {
            returningHeaders["Authorization"] = "Bearer \(token)"
        }
        
        return returningHeaders
    }
}

extension Endpoint {
    static func validateCredentials(isProd: Bool = false) -> Self {
        Endpoint(isProd: isProd, path: "/api/v1/session/")
    }
}

