//
//  ExpensesViewController.m
//  Acordei-Transparencia
//
//  Created by Giovane Possebon on 11/4/15.
//  Copyright (c) 2015 acordei. All rights reserved.
//

#import "ExpensesViewController.h"
#import "ExpensesCell.h"
#import "ApiCall.h"

@interface ExpensesViewController ()

@end

@implementation ExpensesViewController
{
    NSArray *expenses;
}
- (void)viewDidLoad {
    [super viewDidLoad];
    NSString *matricula = [self.fromArray valueForKey:@"matricula"];
    [[[ApiCall alloc] init] callWithUrl:[NSString stringWithFormat:@"http://acordei.cloudapp.net:80/api/politico/gastos/%@", matricula]
                        SuccessCallback:^(NSData *message){
                            expenses = [self populateProfileArray:message];
                            NSLog(@"%@", expenses);
                            dispatch_async(dispatch_get_main_queue(), ^{
                                [self.collectionView reloadData];
                            });
                        }ErrorCallback:^(NSData *erro){
                            NSLog(@"Veio do WS essa mensagem de erro: %@",erro);
                }];
}

-(NSArray *)populateProfileArray:(NSData *)data {
    NSError *error;
    return [NSJSONSerialization JSONObjectWithData:data options:kNilOptions error:&error];
}


-(NSInteger)collectionView:(UICollectionView *)collectionView numberOfItemsInSection:(NSInteger)section {
    return expenses.count;
}

-(UICollectionViewCell *)collectionView:(UICollectionView *)collectionView cellForItemAtIndexPath:(NSIndexPath *)indexPath {
    ExpensesCell *cell = [self.collectionView dequeueReusableCellWithReuseIdentifier:@"cell" forIndexPath:indexPath];
    
    cell.lblExpense.text = [NSString stringWithFormat:@"%@",[[expenses objectAtIndex:indexPath.row]valueForKey:@"tipo"]];
    cell.lblValue.text = [NSString stringWithFormat:@"R$ %@",[[expenses objectAtIndex:indexPath.row]valueForKey:@"valor"]];
    
    return cell;
}

@end
