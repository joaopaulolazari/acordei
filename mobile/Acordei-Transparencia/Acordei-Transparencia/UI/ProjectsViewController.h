//
//  ProjectsViewController.h
//  Acordei-Transparencia
//
//  Created by Giovane Possebon on 11/4/15.
//  Copyright (c) 2015 acordei. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface ProjectsViewController : UIViewController <UICollectionViewDataSource, UICollectionViewDelegate>
@property (strong, nonatomic) IBOutlet UILabel *lblNumberApproved;
@property (strong, nonatomic) IBOutlet UILabel *lblNumberRejected;
@property (strong, nonatomic) IBOutlet UILabel *lblNumberArchived;
@property (strong, nonatomic) IBOutlet UICollectionView *collectionView;

@end
