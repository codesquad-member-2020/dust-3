//
//  LocationManager.swift
//  Dust
//
//  Created by Cloud on 2020/04/02.
//  Copyright © 2020 Cloud. All rights reserved.
//

import Foundation
import CoreLocation

class LocationManager: NSObject {
    
    // MARK: - Properties
    private var locationManager: CLLocationManager = CLLocationManager()
    
    // MARK: - Lifecycles
    override init() {
        super.init()
        locationManager.delegate = self
        locationManager.requestWhenInUseAuthorization()
        locationManager.desiredAccuracy = kCLLocationAccuracyBest
        locationManager.startUpdatingLocation()
    }
    
    deinit {
        removerObserver()
    }
    
    // MARK: - Methods
    private func addObserver(_ value: Any) {
        NotificationCenter.default
            .post(
                name: Notification.Name.pushLoacation,
                object: nil,
                userInfo: ["coordinate": value]
        )
    }
    
    private func removerObserver() {
        NotificationCenter.default
            .removeObserver(
                self,
                name: Notification.Name.pushLoacation,
                object: nil
        )
    }
}

extension LocationManager: CLLocationManagerDelegate {
    func locationManager(_ manager: CLLocationManager, didUpdateLocations locations: [CLLocation]) {
        locationManager.stopUpdatingLocation()
        guard let longitude = locations.first?.coordinate.longitude,
            let latitude = locations.first?.coordinate.latitude else { return }
        addObserver((Float(longitude), Float(latitude)))
        
    }
    
    func locationManager(_ manager: CLLocationManager, didFailWithError error: Error) {
        print("location error is = \(error.localizedDescription)")
    }
}
