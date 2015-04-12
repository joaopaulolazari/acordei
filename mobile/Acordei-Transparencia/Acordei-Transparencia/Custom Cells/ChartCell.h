//
//  ChartCell.h
//  Acordei-Transparencia
//
//  Created by Giovane Possebon on 12/4/15.
//  Copyright (c) 2015 acordei. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "XYPieChart.h"

@interface ChartCell : UICollectionViewCell <XYPieChartDataSource, XYPieChartDelegate, UITableViewDataSource, UITableViewDelegate>
@property (nonatomic) IBOutlet XYPieChart *pieChart;
@property (strong, nonatomic) IBOutlet UILabel *lblTitle;
@property (strong, nonatomic) IBOutlet UILabel *lblName;
@property (strong, nonatomic) IBOutlet UIImageView *imgPolitician;
@property(nonatomic, strong) NSMutableArray *slices;
@property(nonatomic, strong) NSArray        *sliceColors;
@property(nonatomic) NSInteger numberOfSlices;
@property (strong, nonatomic) IBOutlet UITableView *tableView;

@end
