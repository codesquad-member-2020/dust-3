//
//  MainTabBarController.swift
//  Dust
//
//  Created by Cloud on 2020/04/02.
//  Copyright © 2020 Cloud. All rights reserved.
//

import UIKit

class MainTabBarController: UITabBarController {
    
    let fineDustViewController = FineDustViewController()
    let weatherForecastViewController = WeatherForecastViewController()
    
    override func viewDidLoad() {
        super.viewDidLoad()
        viewControllers = [
            fineDustViewController,
            weatherForecastViewController,
        ]
        set(item: fineDustViewController, compare: true)
        set(item: weatherForecastViewController, compare: false)
    }
    
    private func set(item controller: UIViewController, compare: Bool) {
        let item: UITabBarItem = UITabBarItem()
        item.title = compare ? "미세먼지" : "예보"
        item.image = compare ? UIImage(systemName: "smoke.fill") : UIImage(systemName: "clock.fill")
        controller.tabBarItem = item
    }
}

