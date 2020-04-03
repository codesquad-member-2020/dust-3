//
//  NetworkManager.swift
//  Dust
//
//  Created by Cloud on 2020/04/02.
//  Copyright © 2020 Cloud. All rights reserved.
//

import Foundation

protocol NetworkManable {
    func getResource(from: String, handler: @escaping (Data?, Error?) -> ()) throws
}

class NetworkManager {
    
    enum ErrorCase : Error {
        case InvalidURL
        case NotFound
    }
    
    // MARK: - Properties
    let decoder = Decoder()
    
    // MARK: - Lifecycle
    init() {
        NotificationCenter.default
            .addObserver(
                self,
                selector: #selector(addCoordinate),
                name: Notification.Name.pushLoacation,
                object: nil
        )
    }
    
    // MARK: - Methods
    private func getResource(from: String, handler: @escaping (Data?, Error?)->()) throws {
        guard let url = URL(string: from) else {
            throw ErrorCase.InvalidURL
        }
        URLSession.shared.dataTask(with: url) {
            (data, response, error) in
            handler(data, error)
        }.resume()
    }
    
    private func requestAirQualityInfos(_ url: String) {
        try? getResource(from: url) { data, _ in
            guard let data = data,
                let airQualityInfos = self.decoder.decode(data: data) else { return }
            NotificationCenter.default
                .post(
                    name: Notification.Name.pushAirQualityInfos,
                    object: nil,
                    userInfo: ["airQualityInfos": airQualityInfos.airQualityInfos]
            )
        }
    }
    
    // MARK: @objc
    @objc func addCoordinate(_ notifiaction: Notification) {
        let coordinate = notifiaction.userInfo?.values.first as! (x: Float,y: Float)
        let urlString =
        "http://52.78.203.80:8080/air-quality-info?x=\(coordinate.x)&y=\(coordinate.y)"
        requestAirQualityInfos(urlString)
    }
}


