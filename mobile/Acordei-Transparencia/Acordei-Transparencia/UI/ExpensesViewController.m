//
//  ExpensesViewController.m
//  Acordei-Transparencia
//
//  Created by Giovane Possebon on 11/4/15.
//  Copyright (c) 2015 acordei. All rights reserved.
//

#import "ExpensesViewController.h"
#import "ExpensesCell.h"

@interface ExpensesViewController ()

@end

@implementation ExpensesViewController
{
    NSArray *expenses;
    NSArray *values;
}
- (void)viewDidLoad {
    [super viewDidLoad];
    expenses = [self populateExpenses];
    values = [self populateValues];
    // Do any additional setup after loading the view.
}

-(NSArray *)populateExpenses{
    return @[@"Posto de Gasolina", @"Passagens de Avião"];
}

-(NSArray *)populateValues{
    return @[@"1.600,00", @"10.600,00"];
}

-(NSInteger)collectionView:(UICollectionView *)collectionView numberOfItemsInSection:(NSInteger)section {
    return expenses.count;
}

-(UICollectionViewCell *)collectionView:(UICollectionView *)collectionView cellForItemAtIndexPath:(NSIndexPath *)indexPath {
    ExpensesCell *cell = [self.collectionView dequeueReusableCellWithReuseIdentifier:@"cell" forIndexPath:indexPath];
    
    cell.lblExpense.text = [expenses objectAtIndex:indexPath.row];
    cell.lblValue.text = [values objectAtIndex:indexPath.row];
    
    return cell;
}

@end
