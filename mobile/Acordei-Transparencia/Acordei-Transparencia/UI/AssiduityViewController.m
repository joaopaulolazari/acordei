//
//  AssiduityViewController.m
//  Acordei-Transparencia
//
//  Created by Giovane Possebon on 11/4/15.
//  Copyright (c) 2015 acordei. All rights reserved.
//

#import "AssiduityViewController.h"
#import "AssiduityCell.h"
#import "ApiCall.h"
#import "MBProgressHUD.h"

@interface AssiduityViewController ()

@end

@implementation AssiduityViewController
{
    NSArray *days;
}

- (void)viewDidLoad {
    [super viewDidLoad];
    [MBProgressHUD showHUDAddedTo:self.navigationController.view animated:YES];
    NSString *matricula = [self.fromArray valueForKey:@"assiduidadeID"];
    [[[ApiCall alloc] init] callWithUrl:[NSString stringWithFormat:@"http://acordei.cloudapp.net:80/api/politico/assiduidade/%@", matricula]
                        SuccessCallback:^(NSData *message){
                            days = [self populateAssiduity:message];
                            dispatch_async(dispatch_get_main_queue(), ^{
                                [self.collectionView reloadData];
                                [MBProgressHUD hideAllHUDsForView:self.navigationController.view animated:YES];
                            });
                        }ErrorCallback:^(NSData *erro){
                            NSLog(@"Veio do WS essa mensagem de erro: %@",erro);
                        }];
    // Do any additional setup after loading the view.
}

-(NSArray *)populateAssiduity:(NSData *)data{
    NSError *error;
    return [NSJSONSerialization JSONObjectWithData:data options:kNilOptions error:&error];
}


-(NSInteger)collectionView:(UICollectionView *)collectionView numberOfItemsInSection:(NSInteger)section {
    NSArray *array = [days valueForKey:@"eventos"];
    return array.count;
}

-(UICollectionViewCell *)collectionView:(UICollectionView *)collectionView cellForItemAtIndexPath:(NSIndexPath *)indexPath {
    AssiduityCell *cell = [self.collectionView dequeueReusableCellWithReuseIdentifier:@"cell" forIndexPath:indexPath];
    
    cell.lblDay.text = [[[days valueForKey:@"eventos"]objectAtIndex:indexPath.row]valueForKey:@"data"];
    cell.lblDescription.text = [[[days valueForKey:@"eventos"] objectAtIndex:indexPath.row]valueForKey:@"frequenciaNoDia"];
    
    return cell;
}

@end
