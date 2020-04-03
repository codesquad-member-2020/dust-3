//
//  FineDustTableViewCell.swift
//  Dust
//
//  Created by Cloud on 2020/04/02.
//  Copyright © 2020 Cloud. All rights reserved.
//

import UIKit

class FineDustTableViewCell: UITableViewCell {
    
    // MARK: - Properties
    static let identifier: String = "FineDustTableViewCell"
    private var barGraph: UIView!
    private var viewModel: FineDustTableCellViewModel = FineDustTableCellViewModel()
    private var pmLabel: UILabel!
    
    // MARK: - Lifecycle
    override init(style: UITableViewCell.CellStyle, reuseIdentifier: String?) {
        super.init(style: style, reuseIdentifier: reuseIdentifier)
        configure()
    }
    
    required init?(coder: NSCoder) {
        super.init(coder: coder)
        configure()
    }
    
    override func prepareForReuse() {
        resetBarGraphContratints()
    }
    
    // MARK: - Method
    func apply(_ pm: Int) {
        applyBarWidth(pm)
        applyBarColor(pm)
        applyPmTitle(pm)
    }
    
    private func configure() {
        backgroundColor = .clear
        updateBarGraph()
        updatePmLabel()
    }
    
    private func updatePmLabel() {
        pmLabel = UILabel()
        addSubview(pmLabel)
        pmLabel.snp.makeConstraints { make in
            make.bottom.top.trailing.equalToSuperview()
        }
    }
    
    private func updateBarGraph() {
        barGraph = UIView()
        addSubview(barGraph)
        resetBarGraphContratints()
    }
    
    private func resetBarGraphContratints() {
        barGraph.snp.removeConstraints()
        barGraph.snp.makeConstraints { make in
            make.bottom.top.leading.equalToSuperview()
        }
    }
    
    private func applyBarWidth(_ pm: Int) {
        let constratint = viewModel.updateWidth(frame.size.width, pm)
        barGraph.snp.makeConstraints { make in
            make.width.equalToSuperview().multipliedBy(constratint)
        }
    }
    
    private func applyBarColor(_ pm: Int)  {
        barGraph.backgroundColor =  viewModel.updateBarColor(pm)
    }
    
    private func applyPmTitle(_ pm: Int) {
        pmLabel.text = String(pm)
    }
}


