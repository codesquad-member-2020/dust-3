//
//  FineDustTableViewDataSource.swift
//  Dust
//
//  Created by Cloud on 2020/04/02.
//  Copyright © 2020 Cloud. All rights reserved.
//

import UIKit

class FineDustTableViewDataSource: NSObject {
    
    // MARK: - Properties
    private var airQualityInfos: [AirQualityInfos.AirQualityInfo] = []
    
    // MARK: - Lifecycle
    deinit {
        removeObserver()
    }
    
    // MARK: - Methods
    func updateInfos(_ airQualityInfos: [AirQualityInfos.AirQualityInfo]) {
        self.airQualityInfos = []
        self.airQualityInfos = airQualityInfos
        postReloadTableViewNotification()
        addObserver()
    }
    
    private func addObserver() {
        NotificationCenter.default
            .addObserver(
                self,
                selector: #selector(pushAirQualityInfo),
                name: Notification.Name.firstCell,
                object: nil
        )
    }
    
    private func postReloadTableViewNotification() {
        NotificationCenter.default
            .post(
                name: Notification.Name.reloadTableView,
                object: nil
        )
    }
    
    private func postUpdateStatusView(_ status: AirQualityInfos.AirQualityInfo) {
        NotificationCenter.default
            .post(
                name: Notification.Name.updateStatusView,
                object: nil,
                userInfo: ["status" : status]
        )
    }
    
    private func removeObserver() {
        NotificationCenter.default
            .removeObserver(
                self,
                name: Notification.Name.reloadTableView,
                object: nil
        )
        NotificationCenter.default
            .removeObserver(
                self,
                name: Notification.Name.firstCell,
                object: nil
        )
    }
    
    // MARK: @objc
    @objc func pushAirQualityInfo(_ notification: Notification) {
        guard let index = notification.userInfo?.values.first as? Int else { return }
        NotificationCenter.default
            .post(
                name: Notification.Name.updateStatusView,
                object: nil,
                userInfo: ["reloadStatusView": airQualityInfos[index]]
        )
    }
    
}

// MARK: - UITableViewDataSource
extension FineDustTableViewDataSource: UITableViewDataSource {
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return airQualityInfos.count
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        guard let cell = tableView
            .dequeueReusableCell(withIdentifier: FineDustTableViewCell.identifier,for: indexPath) as? FineDustTableViewCell else { return UITableViewCell() }
        let item = airQualityInfos[indexPath.row]
        DispatchQueue.main.async {
            cell.apply(item.pm10)
        }
        let index = tableView.indexPathsForVisibleRows?.first?.item ?? 0
        let status = airQualityInfos[index]
        postUpdateStatusView(status)
        return cell
    }
}

