//
//  DashboardViewController.m
//  Acordei-Transparencia
//
//  Created by Giovane Possebon on 11/4/15.
//  Copyright (c) 2015 acordei. All rights reserved.
//

#import "DashboardViewController.h"
#import "DashboardCell.h"
#import "ApiCall.h"
#import "UIImageView+AFNetworking.h"
#import "ChartCell.h"

@interface DashboardViewController ()
{
    NSArray *values;
}
@end

@implementation DashboardViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    [self pushFromApi];
    
    // Do any additional setup after loading the view.
}

-(void)pushFromApi{
    dispatch_async(dispatch_get_global_queue( DISPATCH_QUEUE_PRIORITY_LOW, 0), ^{
        [[[ApiCall alloc] init] callWithUrl:@"http://acordei.cloudapp.net:80/api/dashboard"
                            SuccessCallback:^(NSData *message){
                                dispatch_async(dispatch_get_main_queue(), ^{
                                    [self populateValues:message];
                                    [self.collectionView reloadData];
                                });
                            }ErrorCallback:^(NSData *erro){
                                NSLog(@"Veio do WS essa mensagem de erro: %@",erro);
                            }];
        [self.collectionView reloadData];
    });
    [self.collectionView reloadData];
}

-(void)populateValues:(NSData *)data {
    NSError *error;
    values = [NSJSONSerialization JSONObjectWithData:data options:kNilOptions error:&error];
}

-(NSInteger)collectionView:(UICollectionView *)collectionView numberOfItemsInSection:(NSInteger)section {
    return values.count;
}

-(UICollectionViewCell *)collectionView:(UICollectionView *)collectionView cellForItemAtIndexPath:(NSIndexPath *)indexPath {
    
    if ([[[values objectAtIndex:indexPath.row]valueForKey:@"tipo"]isEqualToString:@"text"]) {
        DashboardCell *cell = [self.collectionView dequeueReusableCellWithReuseIdentifier:@"cell" forIndexPath:indexPath];
        
        cell.lblTitle.text = [[values objectAtIndex:indexPath.row]valueForKey:@"titulo"];
        cell.lblName.text = [[values objectAtIndex:indexPath.row]valueForKey:@"nomePoliticoRelacionado"];
        cell.lblValue.text = [[values objectAtIndex:indexPath.row]valueForKey:@"conteudo"];
        NSString *imageName = [[values objectAtIndex:indexPath.row]valueForKey:@"fotoPoliticoRelacionado"];
        NSURL *url = [NSURL URLWithString:imageName];
        UIImage *placeholder = [UIImage imageNamed:@"placeholder.png"];
        NSURLRequest *request = [NSURLRequest requestWithURL:url];
        __weak DashboardCell *weakCell = cell;
        [cell.imgProfile setImageWithURLRequest:request
                              placeholderImage:placeholder
                                       success:^(NSURLRequest *request, NSHTTPURLResponse *response, UIImage *image) {
                                           weakCell.imgProfile.image = image;
                                           [weakCell setNeedsLayout];
                                       } failure:nil];
        
        [cell layoutSubviews];
        
        return cell;
    }
    else {
        ChartCell *cell2 = [self.collectionView dequeueReusableCellWithReuseIdentifier:@"cell2" forIndexPath:indexPath];
        cell2.slices = [values objectAtIndex:indexPath.row];
        cell2.numberOfSlices = [[[values objectAtIndex:indexPath.row]valueForKey:@"totalFatias"]integerValue];
        cell2.lblTitle.text = [[values objectAtIndex:indexPath.row]valueForKey:@"titulo"];
        cell2.lblName.text = [[values objectAtIndex:indexPath.row]valueForKey:@"nomePoliticoRelacionado"];
        NSString *imageName = [[values objectAtIndex:indexPath.row]valueForKey:@"fotoPoliticoRelacionado"];
        NSURL *url = [NSURL URLWithString:imageName];
        NSURLRequest *request = [NSURLRequest requestWithURL:url];
        UIImage *placeholder = [UIImage imageNamed:@"placeholder.png"];
        __weak ChartCell *weakCell = cell2;
        [cell2.imgPolitician setImageWithURLRequest:request
                               placeholderImage:placeholder
                                        success:^(NSURLRequest *request, NSHTTPURLResponse *response, UIImage *image) {
                                            weakCell.imgPolitician.image = image;
                                            [weakCell setNeedsLayout];
                                        } failure:nil];
        
        [cell2 layoutSubviews];
        [cell2.pieChart reloadData];
        
        return cell2;
    }
    
}
- (IBAction)didTouchRefresh:(id)sender {
    [self pushFromApi];
}

@end
