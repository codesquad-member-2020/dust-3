//
//  FineDustViewModel.swift
//  Dust
//
//  Created by Cloud on 2020/04/02.
//  Copyright © 2020 Cloud. All rights reserved.
//

import UIKit

class FineDustViewModel {
    
    enum Colors {
        case happy
        case normal
        case mask
        case scream
        
        init(_ status: String) {
            switch status {
            case "좋음":
                self = .happy
            case "보통":
                self = .normal
            case "나쁨":
                self = .mask
            default:
                self = .scream
            }
        }
        
        func generateColor() -> [CGColor] {
            switch self {
            case .happy:
                return [
                    UIColor.systemGreen.cgColor,
                    UIColor.secondarySystemFill.cgColor,
                ]
            case .normal:
                return [
                    UIColor.systemIndigo.cgColor,
                    UIColor.systemTeal.cgColor,
                ]
            case .mask:
                return [
                    UIColor.systemOrange.cgColor,
                    UIColor.systemYellow.cgColor,
                    UIColor.systemBackground.cgColor,
                ]
            default:
                return [
                    UIColor.systemRed.cgColor,
                    UIColor.systemPink.cgColor,
                    UIColor.systemYellow.cgColor,
                ]
            }
        }
    }
    
    func generateGradientColor(status: String) -> [CGColor] {
        Colors(status).generateColor()
    }
}
