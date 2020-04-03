//
//  Protocols.swift
//  Dust
//
//  Created by Cloud on 2020/04/02.
//  Copyright © 2020 Cloud. All rights reserved.
//

import UIKit

protocol ViewMaker {
    func updateWidth(_ width: CGFloat, _ pm: Int) -> CGFloat
    func updateBarColor(_ pm: Int) -> UIColor
}
