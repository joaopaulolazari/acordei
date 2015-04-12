//
//  ExpensesCell.h
//  Acordei-Transparencia
//
//  Created by Giovane Possebon on 11/4/15.
//  Copyright (c) 2015 acordei. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface ExpensesCell : UICollectionViewCell
@property (strong, nonatomic) IBOutlet UILabel *lblExpense;
@property (strong, nonatomic) IBOutlet UILabel *lblValue;

@end
