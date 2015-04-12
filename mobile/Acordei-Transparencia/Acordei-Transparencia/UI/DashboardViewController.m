//
//  DashboardViewController.m
//  Acordei-Transparencia
//
//  Created by Giovane Possebon on 11/4/15.
//  Copyright (c) 2015 acordei. All rights reserved.
//

#import "DashboardViewController.h"
#import "DashboardCell.h"

@interface DashboardViewController ()
{
    NSArray *values;
    NSArray *titles;
}
@end

@implementation DashboardViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    values = [self populateValues];
    titles = [self populateTitles];
    // Do any additional setup after loading the view.
}

- (IBAction)didTouchShareFacebook:(id)sender {
}

-(NSArray *)populateValues {
    return @[@"12", @"R$ 30.000", @"R$ 33.000"];
}

-(NSArray *)populateTitles {
    return @[@"Faltas no Mês", @"Gastos Mensais", @"Gastos Mensais"];
}

-(NSInteger)collectionView:(UICollectionView *)collectionView numberOfItemsInSection:(NSInteger)section {
    return values.count;
}

-(UICollectionViewCell *)collectionView:(UICollectionView *)collectionView cellForItemAtIndexPath:(NSIndexPath *)indexPath {
    DashboardCell *cell = [self.collectionView dequeueReusableCellWithReuseIdentifier:@"cell" forIndexPath:indexPath];
    
    cell.lblValue.text = [values objectAtIndex:indexPath.row];
    cell.lblTitle.text = [titles objectAtIndex:indexPath.row];
    cell.lblName.text = @"Tyrion Lannister";
    cell.imgProfile.image = [UIImage imageNamed:@"tyrion.png"];
    
    [cell layoutSubviews];
    
    return cell;
}

@end
