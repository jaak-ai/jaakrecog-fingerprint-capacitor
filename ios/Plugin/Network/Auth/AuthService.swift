//
//  AuthService.swift
//  Plugin
//
//  Created by Alex on 11/09/21.
//  Copyright © 2021 Max Lynch. All rights reserved.
//

import Foundation

protocol AuthService {
    func validateCredentials(apiKey: String, completion: @escaping((Result<ValidateCredentialsResponse, ApiError>) -> Void))
}

class AuthServiceImplementation: AuthService {
    
    private let apiClient = ApiClient()
    
    func validateCredentials(apiKey: String, completion: @escaping((Result<ValidateCredentialsResponse, ApiError>) -> Void)) {
        let endpoint = Endpoint.validateCredentials()
        self.apiClient.post(url: endpoint.url, headers: endpoint.headers, payload: ValidateCredentialsRequest(apiKey: apiKey), completion: completion)
//        return self.apiClient.post(url: endpoint.url, headers: endpoint.headers, payload: ValidateCredentialsRequest(apiKey: apiKey))
    }
}
