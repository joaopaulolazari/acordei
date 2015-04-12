//
//  BioViewController.m
//  Acordei-Transparencia
//
//  Created by Giovane Possebon on 11/4/15.
//  Copyright (c) 2015 acordei. All rights reserved.
//

#import "BioViewController.h"
#import "ProfilePhotoUtil.h"
#import "ApiCall.h"
#import "MBProgressHUD.h"
#import "UIImageView+AFNetworking.h"

@interface BioViewController ()

@end

@implementation BioViewController
{
    NSArray *biografia;
}

- (void)viewDidLoad {
    [super viewDidLoad];
    biografia = self.fromArray;
    [self populateText];
    // Do any additional setup after loading the view.
}


-(void)populateText{
    self.txtBio.text = [biografia valueForKey:@"biografia"];
    self.lblName.text = [biografia valueForKey:@"nome"];
    
    NSURL *url = [NSURL URLWithString:self.imgLink];
    NSURLRequest *request = [NSURLRequest requestWithURL:url];
    UIImage *placeholder = [UIImage imageNamed:@"placeholder.png"];
    self.imgProfile = [[ProfilePhotoUtil new]photoStyle:self.imgProfile andSize:128];
    [self.imgProfile setImageWithURLRequest:request
                           placeholderImage:placeholder
                                    success:^(NSURLRequest *request, NSHTTPURLResponse *response, UIImage *image) {
                                        self.imgProfile.image = image;
                                    } failure:nil];
}

@end
