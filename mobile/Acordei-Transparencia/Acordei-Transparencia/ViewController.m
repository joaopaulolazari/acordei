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
#import "UIImageView+AFNetworking.h"
#import "MBProgressHUD.h"

@interface ViewController ()

@end

@implementation ViewController
{
    NSArray *json;
}

- (void)viewDidLoad {
    [super viewDidLoad];
    [MBProgressHUD showHUDAddedTo:self.navigationController.view animated:YES];
    dispatch_async(dispatch_get_global_queue( DISPATCH_QUEUE_PRIORITY_LOW, 0), ^{
        [[[ApiCall alloc] init] callWithUrl:@"http://acordei.cloudapp.net:80/api/politicos/"
                            SuccessCallback:^(NSData *message){
                                json = [self populatePoliticiansArray:message];
                                json = [self sortedArray:json];
                                NSLog(@"%@", json);
                                dispatch_async(dispatch_get_main_queue(), ^{
                                    [self.collectionView reloadData];
                                    [MBProgressHUD hideAllHUDsForView:self.navigationController.view animated:YES];
                                });
                            }ErrorCallback:^(NSData *erro){
                                NSLog(@"Veio do WS essa mensagem de erro: %@",erro);
                            }];
    });
}

-(NSArray *)sortedArray:(NSArray *)array {
    NSSortDescriptor *sort = [NSSortDescriptor sortDescriptorWithKey:@"nome" ascending:YES];
    return [array sortedArrayUsingDescriptors:@[sort]];
}

-(NSArray *)populatePoliticiansArray:(NSData *)data {
    NSError *error;
    return [NSJSONSerialization JSONObjectWithData:data options:kNilOptions error:&error];
}

-(NSString *)populatePoliticiansString:(NSData *)data {
    return [[NSString alloc] initWithData:data encoding:NSUTF8StringEncoding];
}

-(NSInteger)collectionView:(UICollectionView *)collectionView numberOfItemsInSection:(NSInteger)section {
    return json.count;
}

-(UICollectionViewCell *)collectionView:(UICollectionView *)collectionView cellForItemAtIndexPath:(NSIndexPath *)indexPath {
    PoliticsCustomCell *cell = [self.collectionView dequeueReusableCellWithReuseIdentifier:@"cell" forIndexPath:indexPath];
    
    cell.lblName.text = [[json objectAtIndex:indexPath.row]valueForKey:@"nome"];
    cell.lblState.text = [[json objectAtIndex:indexPath.row]valueForKey:@"uf"];
    NSString *imageName = [[json objectAtIndex:indexPath.row]valueForKey:@"foto"];
    NSURL *url = [NSURL URLWithString:imageName];
    NSURLRequest *request = [NSURLRequest requestWithURL:url];
    UIImage *placeholder = [UIImage imageNamed:@"placeholder.png"];
    __weak PoliticsCustomCell *weakCell = cell;
    [cell.imgPerson setImageWithURLRequest:request
                          placeholderImage:placeholder
                                   success:^(NSURLRequest *request, NSHTTPURLResponse *response, UIImage *image) {
                                       weakCell.imgPerson.image = image;
                                       [weakCell setNeedsLayout];
                                   } failure:nil];
    
    [cell layoutSubviews];
    
    return cell;
}

-(void)collectionView:(UICollectionView *)collectionView didSelectItemAtIndexPath:(NSIndexPath *)indexPath {
    ProfileViewController *pvc = [self.storyboard instantiateViewControllerWithIdentifier:@"Profile"];
    pvc.hidesBottomBarWhenPushed = YES;
    pvc.title = @"Perfil";
    pvc.fromListArray = [json objectAtIndex:indexPath.row];
    [self.navigationController pushViewController:pvc animated:YES];
}

@end
