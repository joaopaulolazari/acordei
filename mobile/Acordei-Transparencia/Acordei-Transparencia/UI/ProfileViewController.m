//
//  ProfileViewController.m
//  Acordei-Transparencia
//
//  Created by Giovane Possebon on 11/4/15.
//  Copyright (c) 2015 acordei. All rights reserved.
//

#import "ProfileViewController.h"
#import "ProfileCustomCell.h"

@interface ProfileViewController ()

@end

@implementation ProfileViewController {
    NSArray *iconImages;
    NSArray *categoryNames;
}

- (void)viewDidLoad {
    [super viewDidLoad];
    iconImages = [self populateIconsInArray];
    categoryNames = [self populateCategoryNames];
}

- (NSArray *)populateIconsInArray{
    return @[@"icon-projects.png", @"icon-charts.png", @"icon-bio.png", @"icon-money.png", @"icon-ok.png"];
}

- (NSArray *)populateCategoryNames{
    return @[@"Projetos", @"Estatísticas", @"Biografia", @"Gastos", @"Assiduidade"];
}

-(NSInteger)collectionView:(UICollectionView *)collectionView numberOfItemsInSection:(NSInteger)section {
    return iconImages.count;
}

-(UICollectionViewCell *)collectionView:(UICollectionView *)collectionView cellForItemAtIndexPath:(NSIndexPath *)indexPath {
    ProfileCustomCell *cell = [self.collectionView dequeueReusableCellWithReuseIdentifier:@"cell" forIndexPath:indexPath];
    
    cell.lblCategory.text = [categoryNames objectAtIndex:indexPath.row];
    cell.imgIcon.image = [UIImage imageNamed:[iconImages objectAtIndex:indexPath.row]];
    
    return cell;
}


@end
