//
//  DateManager.swift
//  Dust
//
//  Created by Cloud on 2020/04/02.
//  Copyright © 2020 Cloud. All rights reserved.
//

import Foundation

struct DateManager {
    
    let formatter: DateFormatter = {
        let formatter = DateFormatter()
        formatter.dateFormat = "dd"
        return formatter
    }()
    
    func generateDay() -> String {
        formatter.string(from: Date())
    }
    
    func compare(_ input: String) -> String {
        input == generateDay() ? "오늘" : "어제"
    }
}

