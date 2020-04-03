//
//  FineDustView.swift
//  Dust
//
//  Created by Cloud on 2020/04/02.
//  Copyright © 2020 Cloud. All rights reserved.
//

import UIKit
import SnapKit

class FineDustView: UIView {
    
    // MARK: - Properties
    var tableView: UITableView!
    private var statusView: FineDustStatusView!
    private var viewModel: FineDustViewModel = FineDustViewModel()
    private var gradientLayer = CAGradientLayer()
    
    // Lifecycle
    override init(frame: CGRect) {
        super.init(frame: frame)
        configure()
    }
    
    required init?(coder: NSCoder) {
        super.init(coder: coder)
        configure()
    }
    
    override func layoutSublayers(of layer: CALayer) {
        super.layoutSublayers(of: layer)
        gradientLayer.frame = frame
    }
    
    deinit {
        deinitObserver()
    }
    
    // MARK: - Methods
    private func configure() {
        layer.addSublayer(gradientLayer)
        configureStatusView()
        configureTableView()
        addObserver()
    }
    
    private func configureTableView() {
        tableView = UITableView()
        addSubview(tableView)
        tableView
            .register(
                FineDustTableViewCell.self,
                forCellReuseIdentifier: FineDustTableViewCell.identifier
        )
        tableView.backgroundColor = .clear
        tableView.snp.makeConstraints { make in
            make.bottom.leading.trailing.equalToSuperview()
            make.top.equalTo(statusView.snp.bottom)
        }
    }
    
    private func configureStatusView() {
        statusView = FineDustStatusView()
        addSubview(statusView)
        statusView.snp.makeConstraints { (make) in
            make.top.leading.trailing.equalToSuperview()
            make.height.equalTo(statusView.snp.width)
        }
    }
    
    private func addObserver() {
        NotificationCenter.default
            .addObserver(
                self,
                selector: #selector(reloadTableView),
                name: Notification.Name.reloadTableView,
                object: nil
        )
        NotificationCenter.default
            .addObserver(
                self,
                selector: #selector(updateColor),
                name: Notification.Name.updateColor,
                object: nil
        )
    }
    
    private func deinitObserver() {
        NotificationCenter.default
            .removeObserver(
                self,
                name: Notification.Name.reloadTableView,
                object: nil
        )
        NotificationCenter.default
            .removeObserver(
                self,
                name: Notification.Name.updateColor,
                object: nil
        )
    }
    
    // MARK: @objc
    @objc func updateColor(_ notification: Notification) {
        let userInfo = notification.userInfo?.values.first as! String
        gradientLayer.colors = viewModel.generateGradientColor(status: userInfo)
    }
    
    @objc func reloadTableView() {
        DispatchQueue.main.async {
            self.tableView.reloadData()
        }
    }
}
