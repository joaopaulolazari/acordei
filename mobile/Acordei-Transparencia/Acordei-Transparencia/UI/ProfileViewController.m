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
#import "DashboardViewController.h"
#import "BioViewController.h"
#import "AssiduityViewController.h"
#import "UIImageView+AFNetworking.h"
#import "ProfilePhotoUtil.h"
#import "ApiCall.h"

@interface ProfileViewController ()

@end

@implementation ProfileViewController {
    NSArray *iconImages;
    NSArray *categoryNames;
    NSArray *profile;
}

- (void)viewDidLoad {
    [super viewDidLoad];
    
    dispatch_async(dispatch_get_global_queue( DISPATCH_QUEUE_PRIORITY_LOW, 0), ^{
        NSString *matricula = [self.fromListArray valueForKey:@"matricula"];
        [[[ApiCall alloc] init] callWithUrl:[NSString stringWithFormat:@"http://acordei.cloudapp.net:80/api/politico/%@", matricula]
                            SuccessCallback:^(NSData *message){
                                profile = [self populateProfileArray:message];
                                dispatch_async(dispatch_get_main_queue(), ^{
                                    [self populateInfo];
                                });
                            }ErrorCallback:^(NSData *erro){
                                NSLog(@"Veio do WS essa mensagem de erro: %@",erro);
                            }];
    });
    iconImages = [self populateIconsInArray];
    categoryNames = [self populateCategoryNames];
}

-(NSArray *)populateProfileArray:(NSData *)data {
    NSError *error;
    return [NSJSONSerialization JSONObjectWithData:data options:kNilOptions error:&error];
}

-(void)populateInfo {
    NSLog(@"%@", profile);
    self.lblName.text = [profile valueForKey:@"nome"];
    self.lblEmail.text = [profile valueForKey:@"email"];
    self.lblSituation.text = [profile valueForKey:@"situacao"];
    NSURL *url = [NSURL URLWithString:[self.fromListArray valueForKey:@"foto"]];
    NSURLRequest *request = [NSURLRequest requestWithURL:url];
    self.imgProfile = [[ProfilePhotoUtil new]photoStyle:self.imgProfile andSize:109];
    [self.imgProfile setImageWithURLRequest:request
                          placeholderImage:nil
                                   success:^(NSURLRequest *request, NSHTTPURLResponse *response, UIImage *image) {
                                       self.imgProfile.image = image;
                                   } failure:nil];
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

- (IBAction)touchEmail:(id)sender {
    NSString *emailTitle = @"";

    NSString *messageBody = @"via Acordei";

    NSArray *toRecipents = [NSArray arrayWithObject:self.lblEmail.text];
    
    MFMailComposeViewController *mc = [[MFMailComposeViewController alloc] init];
    mc.mailComposeDelegate = self;
    [mc setSubject:emailTitle];
    [mc setMessageBody:messageBody isHTML:NO];
    [mc setToRecipients:toRecipents];
    
    [self presentViewController:mc animated:YES completion:NULL];
    
}

- (void) mailComposeController:(MFMailComposeViewController *)controller didFinishWithResult:(MFMailComposeResult)result error:(NSError *)error
{
    switch (result)
    {
        case MFMailComposeResultCancelled:
            NSLog(@"Mail cancelled");
            break;
        case MFMailComposeResultSaved:
            NSLog(@"Mail saved");
            break;
        case MFMailComposeResultSent:
            NSLog(@"Mail sent");
            break;
        case MFMailComposeResultFailed:
            NSLog(@"Mail sent failure: %@", [error localizedDescription]);
            break;
        default:
            break;
    }
    
    // Close the Mail Interface
    [self dismissViewControllerAnimated:YES completion:NULL];
}

-(void)collectionView:(UICollectionView *)collectionView didSelectItemAtIndexPath:(NSIndexPath *)indexPath {
    [self.collectionView deselectItemAtIndexPath:indexPath animated:NO];
    if (indexPath.row == 0) {
        ProjectsViewController *pvc = [self.storyboard instantiateViewControllerWithIdentifier:@"Projects"];
        pvc.title = @"Projetos";
        pvc.fromListArray = profile;
        [self.navigationController pushViewController:pvc animated:YES];
    }
    
    else if (indexPath.row == 1){
        DashboardViewController *dvc = [self.storyboard instantiateViewControllerWithIdentifier:@"Dashboard"];
        dvc.title = @"Dashboard";
        [self.navigationController pushViewController:dvc animated:YES];
    }
    
    else if (indexPath.row == 2){
        BioViewController *bvc = [self.storyboard instantiateViewControllerWithIdentifier:@"Bio"];
        bvc.title = @"Biografia";
        [self.navigationController pushViewController:bvc animated:YES];
    }
    
    else if (indexPath.row == 3) {
        ExpensesViewController *evc = [self.storyboard instantiateViewControllerWithIdentifier:@"Expenses"];
        evc.title = @"Gastos";
        [self.navigationController pushViewController:evc animated:YES];
    }
    
    else if (indexPath.row == 4){
        AssiduityViewController *avc = [self.storyboard instantiateViewControllerWithIdentifier:@"Assiduity"];
        avc.title = @"Assiduidade";
        [self.navigationController pushViewController:avc animated:YES];
    }
}


@end
