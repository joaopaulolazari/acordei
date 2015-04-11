//
//  ViewController.m
//  Acordei-Transparencia
//
//  Created by Giovane Possebon on 11/4/15.
//  Copyright (c) 2015 acordei. All rights reserved.
//

#import "ViewController.h"
#import "ApiCall.h"
#import "CacheAPI.h"
#import "PoliticsCustomCell.h"
#import "ProfileViewController.h"

@interface ViewController ()

@end

    NSArray *name;
    NSArray *role;

@implementation ViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    name = @[@"Teste 1", @"Teste 2", @"Teste 3", @"Teste 1", @"Teste 2", @"Teste 3"];
    role = @[@"Cargo 1", @"Cargo 2", @"Cargo 3", @"Cargo 1", @"Cargo 2", @"Cargo 3"];
    [[[ApiCall alloc] init]
        callWithUrl:@"https://www.kimonolabs.com/api/28d5kkdo?apikey=10deb955005b151ee7f6d2d2c796cde6"
        SuccessCallback:^(NSString *message){
            NSLog(@"Veio do WS essa mensagem de successo: %@",message);
        }ErrorCallback:^(NSString *erro){
            NSLog(@"Veio do WS essa mensagem de erro: %@",erro);
        }];
    
//    [[CacheAPI sharedDatabase] putByKey:@"deivison" json:@"{id:1}"];
//    NSLog(@"From database by deivison => %@",[[CacheAPI sharedDatabase] getByKey:@"deivison"]);
}

-(NSInteger)collectionView:(UICollectionView *)collectionView numberOfItemsInSection:(NSInteger)section {
    return name.count;
}

-(UICollectionViewCell *)collectionView:(UICollectionView *)collectionView cellForItemAtIndexPath:(NSIndexPath *)indexPath {
    PoliticsCustomCell *cell = [self.collectionView dequeueReusableCellWithReuseIdentifier:@"cell" forIndexPath:indexPath];
    
    cell.lblName.text = [name objectAtIndex:indexPath.row];
    cell.lblRole.text = [role objectAtIndex:indexPath.row];
    cell.imgPerson.image = [UIImage imageNamed:@"tyrion.png"];
    
    [cell layoutSubviews];
    
    return cell;
}

-(void)collectionView:(UICollectionView *)collectionView didSelectItemAtIndexPath:(NSIndexPath *)indexPath {
    ProfileViewController *pvc = [self.storyboard instantiateViewControllerWithIdentifier:@"Profile"];
    pvc.hidesBottomBarWhenPushed = YES;
    pvc.title = [name objectAtIndex:indexPath.row];
    [self.navigationController pushViewController:pvc animated:YES];
}

@end
