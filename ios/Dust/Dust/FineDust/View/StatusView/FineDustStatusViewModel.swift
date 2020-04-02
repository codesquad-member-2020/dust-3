//
//  FineDustStatusViewModel.swift
//  Dust
//
//  Created by Cloud on 2020/04/02.
//  Copyright © 2020 Cloud. All rights reserved.
//

import UIKit

class FineDustStatusViewModel: NSObject {
    
    enum Emoji {
        case happy
        case normal
        case mask
        case scream
        
        init(_ pm: Int) {
            switch pm {
            case 1...49:
                self = .happy
            case 50...99:
                self = .normal
            case 100...149:
                self = .mask
            default:
                self = .scream
            }
        }
        
        var unicode: String {
            switch self {
            case .happy:
                return "\u{1F600}"
            case .normal:
                return "\u{1F642}"
            case .mask:
                return "\u{1F637}"
            default:
                return "\u{1F631}"
            }
            
        }
    }
    
    private var dateManager = DateManager()
    
    func generateLocation(_ input: String) -> NSMutableAttributedString {
        let fontSize = UIFont.boldSystemFont(ofSize: 17)
        let location = input + " 측정소 기준"
        let attributeString = NSMutableAttributedString(string: location)
        attributeString
            .addAttribute(
                NSAttributedString.Key(rawValue: kCTFontAttributeName as String),
                value: fontSize,
                range: (location as NSString).range(of: input)
        )
        return attributeString
    }
    
    func generateTime(_ date: String) -> String {
        let today = String(date.split(separator: " ").first?.split(separator: "-").last ?? "")
        return dateManager.compare(today) + " " + String(date.split(separator: " ").last ?? " ")
    }
    
    func generatePm(_ pm: Int) -> String {
        String(pm) + "𝜇g/m\u{00B3}"
    }
    
    func generateEmoji(_ pm: Int) -> String {
        Emoji(pm).unicode
    }
}

