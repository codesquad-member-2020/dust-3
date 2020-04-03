//
//  StatusView.swift
//  Dust
//
//  Created by Cloud on 2020/04/02.
//  Copyright © 2020 Cloud. All rights reserved.
//

import UIKit
import SnapKit

class FineDustStatusView: UIView {
    
    // MARK: - Properties
    private var titleLabel: UILabel!
    private var emojiLabel: UILabel!
    private var statusLabel: UILabel!
    private var measurand: UILabel!
    private var measurementTime: UILabel!
    private var positionLabel: UILabel!
    private var viewModel: FineDustStatusViewModel = FineDustStatusViewModel()
    
    // MARK: - Lifecycles
    override init(frame: CGRect) {
        super.init(frame: frame)
        configure()
        
    }
    
    required init?(coder: NSCoder) {
        super.init(coder: coder)
        configure()
    }
    
    deinit {
        removeObserver()
    }
    
    // MARK: - Methods
    private func configure() {
        configureTitleLabel()
        configureEmojiLabel()
        configureStatusLabel()
        configureMeasurandLabel()
        configureMeasurementTimeLabel()
        configurePositionLabel()
        addObserver()
    }
    
    private func configureTitleLabel() {
        titleLabel = UILabel()
        addSubview(titleLabel)
        titleLabel.text = "미세먼지 앱"
        titleLabel.font = UIFont.boldSystemFont(ofSize: 40)
        titleLabel.textAlignment = .center
        titleLabel.snp.makeConstraints { make in
            make.centerX.equalToSuperview()
            make.top.equalTo(safeAreaLayoutGuide.snp.top).inset(24)
            make.leading.equalToSuperview().inset(40)
        }
    }
    
    private func configureEmojiLabel() {
        emojiLabel = UILabel()
        addSubview(emojiLabel)
        emojiLabel.font = UIFont.systemFont(ofSize: 120)
        emojiLabel.textAlignment = .center
        emojiLabel.snp.makeConstraints { make in
            make.centerX.equalToSuperview()
            make.top.equalTo(titleLabel.snp.bottom).inset(-8)
            make.leading.equalTo(titleLabel.snp.leading).inset(64)
        }
    }
    
    private func configureStatusLabel() {
        statusLabel = UILabel()
        addSubview(statusLabel)
        statusLabel.textAlignment = .center
        statusLabel.font = UIFont.boldSystemFont(ofSize: 28)
        statusLabel.snp.makeConstraints { make in
            make.centerX.equalToSuperview()
            make.top.equalTo(emojiLabel.snp.bottom)
            make.leading.equalTo(emojiLabel.snp.leading).inset(30)
        }
    }
    
    private func configureMeasurandLabel() {
        measurand = UILabel()
        addSubview(measurand)
        measurand.textAlignment = .center
        measurand.font = UIFont.boldSystemFont(ofSize: 24)
        measurand.snp.makeConstraints { (make) in
            make.top.equalTo(statusLabel.snp.bottom)
            make.leading.equalToSuperview().inset(40)
        }
    }
    
    private func configureMeasurementTimeLabel() {
        measurementTime = UILabel()
        measurementTime.text = " "
        addSubview(measurementTime)
        measurementTime.font = UIFont.systemFont(ofSize: 24)
        measurementTime.font = UIFont.boldSystemFont(ofSize: 24)
        measurementTime.snp.makeConstraints { make in
            make.top.equalTo(measurand.snp.top)
            make.bottom.equalTo(measurand.snp.bottom)
            make.trailing.equalToSuperview().inset(40)
        }
    }
    
    private func configurePositionLabel() {
        positionLabel = UILabel()
        addSubview(positionLabel)
        positionLabel.textAlignment = .center
        positionLabel.snp.makeConstraints { (make) in
            make.top.equalTo(measurementTime.snp.bottom).inset(-8)
            make.centerX.equalToSuperview()
            make.leading.equalTo(100)
        }
    }
    
    private func addObserver() {
        NotificationCenter.default
            .addObserver(
                self,
                selector: #selector(updateStatusView),
                name: Notification.Name.updateStatusView,
                object: nil
        )
    }
    
    private func removeObserver() {
        NotificationCenter.default
            .removeObserver(
                self,
                name: Notification.Name.updateStatusView,
                object: nil
        )
    }
    
    private func postNotification(status: String) {
        NotificationCenter.default
            .post(
                name: Notification.Name.updateColor,
                object: nil,
                userInfo: ["status": status]
        )
    }
    
    private func updateView(airQualityInfo: AirQualityInfos.AirQualityInfo) {
        statusLabel.text = airQualityInfo.airQualityIndex
        measurementTime.text = viewModel.generateTime(airQualityInfo.currentTime)
        positionLabel.attributedText = viewModel.generateLocation(airQualityInfo.location)
        measurand.text = viewModel.generatePm(airQualityInfo.pm10)
        emojiLabel.text = viewModel.generateEmoji(airQualityInfo.pm10)
    }
    
    // MARK: @objc
    @objc func updateStatusView(_ notifiaction: Notification) {
        guard let airQualityInfo = notifiaction.userInfo?.values.first
            as? AirQualityInfos.AirQualityInfo else { return }
        updateView(airQualityInfo: airQualityInfo)
        postNotification(status: airQualityInfo.airQualityIndex)
    }
}

