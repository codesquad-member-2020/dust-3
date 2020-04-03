//
//  AirQualityInfos.swift
//  Dust
//
//  Created by Cloud on 2020/04/02.
//  Copyright © 2020 Cloud. All rights reserved.
//

import Foundation

struct AirQualityInfos: Codable {
    
    struct AirQualityInfo: Codable {
        var airQualityIndex: String
        var currentTime: String
        var location: String
        var pm10: Int
    }
    
    var airQualityInfos: [AirQualityInfo]
}
