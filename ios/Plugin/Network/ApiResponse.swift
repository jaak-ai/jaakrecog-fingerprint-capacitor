//
//  ApiResponse.swift
//  Plugin
//
//  Created by Alex on 11/09/21.
//  Copyright © 2021 Max Lynch. All rights reserved.
//

import Foundation

/// Represents a generic response, adapt it to standard response object (Jaakrecog)
struct ApiResponse: Codable {
    
    /// If true everything went well
    let success: Bool
    
    /// Stores the message
    let message: String?
}
