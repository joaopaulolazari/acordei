//
//  ProfileViewController.m
//  Acordei-Transparencia
//
//  Created by Giovane Possebon on 11/4/15.
//  Copyright (c) 2015 acordei. All rights reserved.
//

#import "ProfileViewController.h"
#import "ProfileCustomCell.h"
#import "ProfilePhotoUtil.h"
#import "ProjectsViewController.h"
#import "ExpensesViewController.h"

@interface ProfileViewController ()

@end

@implementation ProfileViewController {
    NSArray *iconImages;
    NSArray *categoryNames;
}

- (void)viewDidLoad {
    [super viewDidLoad];
    self.imgProfile = [[ProfilePhotoUtil new]photoStyle:self.imgProfile andSize:116];
    [self.imgProfile setImage:[UIImage imageNamed:@"tyrion.png"]];
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

-(void)collectionView:(UICollectionView *)collectionView didSelectItemAtIndexPath:(NSIndexPath *)indexPath {
    [self.collectionView deselectItemAtIndexPath:indexPath animated:NO];
    if (indexPath.row == 0) {
        ProjectsViewController *pvc = [self.storyboard instantiateViewControllerWithIdentifier:@"Projects"];
        [self.navigationController pushViewController:pvc animated:YES];
    }
    
    
    else if (indexPath.row == 3) {
        ExpensesViewController *evc = [self.storyboard instantiateViewControllerWithIdentifier:@"Expenses"];
        [self.navigationController pushViewController:evc animated:YES];
    }
}


@end
