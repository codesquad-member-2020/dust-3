//
//  FineDustViewController.swift
//  Dust
//
//  Created by Cloud on 2020/04/02.
//  Copyright © 2020 Cloud. All rights reserved.
//

import UIKit

class FineDustViewController: UIViewController {
    
    // MARK: - Properties
    private var fineDustView: FineDustView! = FineDustView()
    private var fineDustTableViewDataSource = FineDustTableViewDataSource()
    private var networkManager: NetworkManager = NetworkManager()
    private var locationManager: LocationManager = LocationManager()
    
    // MARK: - Lifecycle
    override func loadView() {
        view = fineDustView
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        fineDustView.tableView.dataSource = fineDustTableViewDataSource
        fineDustView.tableView.delegate = self
        addObserver()
    }
    
    deinit {
        removeObserver()
    }
    
    // MARK: - Methods
    private func addObserver() {
        NotificationCenter.default
            .addObserver(
                self,
                selector: #selector(updateView),
                name: Notification.Name.pushAirQualityInfos,
                object: nil
        )
    }
    
    private func removeObserver() {
        NotificationCenter.default
        .removeObserver(
            self,
            name: Notification.Name.pushAirQualityInfos,
            object: nil
        )
    }
    
    // MARK: - @objc
    @objc func updateView(_ notification: Notification) {
        guard let airQualityInfos = notification.userInfo?.first?.value
            as? [AirQualityInfos.AirQualityInfo] else { return }
        fineDustTableViewDataSource.updateInfos(airQualityInfos)
    }
}

extension FineDustViewController: UITableViewDelegate {
    func tableView(_ tableView: UITableView, heightForRowAt indexPath: IndexPath) -> CGFloat {
        return UITableView.cellHeight
    }
    
    func scrollViewDidScroll(_ scrollView: UIScrollView) {
        let tableview = scrollView as! UITableView
        guard let fisrtCell = tableview.visibleCells.first,
            let index = tableview.indexPath(for: fisrtCell)?.item else { return }
        NotificationCenter.default
            .post(
                name: Notification.Name.firstCell,
                object: nil,
                userInfo: ["index": index]
        )
    }
}

extension UITableView {
    static var cellHeight: CGFloat  = 22
}
