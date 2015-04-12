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
#import "ChartCell.h"

@interface DashboardViewController ()
{
    NSArray *values;
}
@end

@implementation DashboardViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    dispatch_async(dispatch_get_global_queue( DISPATCH_QUEUE_PRIORITY_LOW, 0), ^{
        [[[ApiCall alloc] init] callWithUrl:@"http://acordei.cloudapp.net:80/api/dashboard"
                            SuccessCallback:^(NSData *message){
                                values = [self populateValues:message];
                                dispatch_async(dispatch_get_main_queue(), ^{
                                    NSLog(@"%@", values);
                                    [self.collectionView reloadData];
                                });
                            }ErrorCallback:^(NSData *erro){
                                NSLog(@"Veio do WS essa mensagem de erro: %@",erro);
                            }];
    });
    // Do any additional setup after loading the view.
}

- (IBAction)didTouchShareFacebook:(id)sender {
}

-(NSArray *)populateValues:(NSData *)data {
    NSError *error;
    return [NSJSONSerialization JSONObjectWithData:data options:kNilOptions error:&error];
}

-(NSInteger)collectionView:(UICollectionView *)collectionView numberOfItemsInSection:(NSInteger)section {
    return values.count;
}

-(UICollectionViewCell *)collectionView:(UICollectionView *)collectionView cellForItemAtIndexPath:(NSIndexPath *)indexPath {
    
    if ([[[values objectAtIndex:indexPath.row]valueForKey:@"tipo"]isEqualToString:@"text"]) {
        DashboardCell *cell = [self.collectionView dequeueReusableCellWithReuseIdentifier:@"cell" forIndexPath:indexPath];
        
        cell.lblTitle.text = [[values objectAtIndex:indexPath.row]valueForKey:@"titulo"];
        cell.lblName.text = @"TEste";
        cell.lblValue.text = [[values objectAtIndex:indexPath.row]valueForKey:@"conteudo"];
        cell.imgProfile.image = [UIImage imageNamed:@"tyrion.png"];
        
        [cell layoutSubviews];
        
        return cell;
    }
    else {
        ChartCell *cell2 = [self.collectionView dequeueReusableCellWithReuseIdentifier:@"cell2" forIndexPath:indexPath];
        
        cell2.slices = [values objectAtIndex:indexPath.row];
        cell2.numberOfSlices = [[[values objectAtIndex:indexPath.row]valueForKey:@"totalFatias"]integerValue];
        [cell2.pieChart reloadData];
        
        return cell2;
    }
    
}

@end
