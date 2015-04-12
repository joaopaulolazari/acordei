//
//  AssiduityViewController.m
//  Acordei-Transparencia
//
//  Created by Giovane Possebon on 11/4/15.
//  Copyright (c) 2015 acordei. All rights reserved.
//

#import "AssiduityViewController.h"
#import "AssiduityCell.h"

@interface AssiduityViewController ()

@end

@implementation AssiduityViewController
{
    NSArray *days;
    NSArray *description;
}

- (void)viewDidLoad {
    [super viewDidLoad];
    days = [self populateDays];
    description = [self populateDescription];
    // Do any additional setup after loading the view.
}

-(NSArray *)populateDescription{
    return @[@"Presente", @"Presente", @"Ausente", @"Ausência Justificada"];
}

-(NSArray *)populateDays{
    return @[@"01/01/2015", @"02/01/2015", @"03/01/2015", @"04/01/2015"];
}

-(NSInteger)collectionView:(UICollectionView *)collectionView numberOfItemsInSection:(NSInteger)section {
    return days.count;
}

-(UICollectionViewCell *)collectionView:(UICollectionView *)collectionView cellForItemAtIndexPath:(NSIndexPath *)indexPath {
    AssiduityCell *cell = [self.collectionView dequeueReusableCellWithReuseIdentifier:@"cell" forIndexPath:indexPath];
    
    cell.lblDay.text = [days objectAtIndex:indexPath.row];
    cell.lblDescription.text = [description objectAtIndex:indexPath.row];
    
    return cell;
}

@end
