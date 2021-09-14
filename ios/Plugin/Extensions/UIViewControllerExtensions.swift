//
//  UIViewControllerExtensions.swift
//  Plugin
//
//  Created by Alex on 09/09/21.
//  Copyright © 2021 Max Lynch. All rights reserved.
//

import UIKit
import Foundation

extension UIViewController {
    static func loadFromNib(bundle: Bundle? = nil) -> Self {
        func instantiateFromNib<T: UIViewController>() -> T {
            return T.init(nibName: String(describing: T.self), bundle: bundle)
        }
        
        return instantiateFromNib()
    }
}

