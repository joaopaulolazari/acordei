//
//  DashboardCell.h
//  Acordei-Transparencia
//
//  Created by Giovane Possebon on 11/4/15.
//  Copyright (c) 2015 acordei. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface DashboardCell : UICollectionViewCell
@property (strong, nonatomic) IBOutlet UILabel *lblValue;
@property (strong, nonatomic) IBOutlet UILabel *lblTitle;
@property (strong, nonatomic) IBOutlet UILabel *lblName;
@property (strong, nonatomic) IBOutlet UIImageView *imgProfile;
@end
