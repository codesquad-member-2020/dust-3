//
//  FineDustTableViewModel.swift
//  Dust
//
//  Created by Cloud on 2020/04/02.
//  Copyright © 2020 Cloud. All rights reserved.
//

import UIKit

struct FineDustTableCellViewModel: ViewMaker {
    
    enum ColorGenerator {
        case green
        case yellow
        case orange
        case red
        
        init(_ pm: Int) {
            switch pm {
            case 1...30:
                self = .green
            case 31...80:
                self = .yellow
            case 81...150:
                self = .orange
            default:
                self = .red
            }
        }
        
        func measure() -> UIColor {
            switch self {
            case .green:
                return .systemGreen
            case .yellow:
                return .systemYellow
            case .orange:
                return .systemOrange
            default:
                return .systemRed
            }
        }
    }
    
    func updateWidth(_ width: CGFloat, _ pm: Int) -> CGFloat {
        return (width / 100) * CGFloat(pm) * 0.5 / width
    }
    
    func updateBarColor(_ pm: Int) -> UIColor {
        ColorGenerator(pm).measure()
    }
}
