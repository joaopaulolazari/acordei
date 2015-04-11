//
//  ProjectsViewController.m
//  Acordei-Transparencia
//
//  Created by Giovane Possebon on 11/4/15.
//  Copyright (c) 2015 acordei. All rights reserved.
//

#import "ProjectsViewController.h"
#import "ProjectsCell.h"

@interface ProjectsViewController ()

@end

@implementation ProjectsViewController
{
    NSArray *projects;
    NSArray *status;
}

- (void)viewDidLoad {
    [super viewDidLoad];
    projects = [self populateProjects];
    status = [self populateStatus];
    // Do any additional setup after loading the view.
}

-(NSArray *)populateProjects{
    return @[@"Projeto muito louco, bla bla, para testar o campo de texto", @"Esse projeto também é um teste, e ele foi arquivado porque era muito palha"];
}

-(NSArray *)populateStatus{
    return @[@"green-check.png", @"icon-archived.png"];
}

-(NSInteger)collectionView:(UICollectionView *)collectionView numberOfItemsInSection:(NSInteger)section {
    return projects.count;
}

-(UICollectionViewCell *)collectionView:(UICollectionView *)collectionView cellForItemAtIndexPath:(NSIndexPath *)indexPath {
    ProjectsCell *cell = [self.collectionView dequeueReusableCellWithReuseIdentifier:@"cell" forIndexPath:indexPath];
    
    cell.lblProject.text = [projects objectAtIndex:indexPath.row];
    cell.imgStatus.image = [UIImage imageNamed:[status objectAtIndex:indexPath.row]];
    
    return cell;
}


@end
