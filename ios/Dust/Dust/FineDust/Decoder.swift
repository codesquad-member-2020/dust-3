//
//  Decoder.swift
//  Dust
//
//  Created by Cloud on 2020/04/02.
//  Copyright © 2020 Cloud. All rights reserved.
//

import Foundation

struct Decoder {
    let decoder = JSONDecoder()
    
    func decode(data: Data) -> AirQualityInfos? {
        do {
            return try decoder.decode(AirQualityInfos.self, from: data)
        } catch {
            print(error)
            return nil
        }
    }
}
