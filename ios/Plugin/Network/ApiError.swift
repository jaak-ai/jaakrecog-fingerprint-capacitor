//
//  ApiError.swift
//  Plugin
//
//  Created by Alex on 11/09/21.
//  Copyright © 2021 Max Lynch. All rights reserved.
//

import Foundation

enum ApiError: Error {
    case invalidBody
    case invalidEndpoint
    case invalidURL
    case emptyData
    case invalidJSON
    case invalidResponse
    case statusCode(Int)
    case unableToObtainToken
    case serverError//(ApiResponse)
    
//    func message() -> String? {
//        switch self {
//        case .serverError(let response):
//            return response.message
//        case .stripeError(let response):
//            return response.message
//        default:
//            return self.localizedDescription
//        }
//    }
}
