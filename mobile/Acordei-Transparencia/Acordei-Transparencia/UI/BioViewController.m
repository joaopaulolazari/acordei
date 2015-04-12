//
//  BioViewController.m
//  Acordei-Transparencia
//
//  Created by Giovane Possebon on 11/4/15.
//  Copyright (c) 2015 acordei. All rights reserved.
//

#import "BioViewController.h"
#import "ProfilePhotoUtil.h"

@interface BioViewController ()

@end

@implementation BioViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    self.imgProfile = [[ProfilePhotoUtil new]photoStyle:self.imgProfile andSize:128];
    self.imgProfile.image = [UIImage imageNamed:@"tyrion.png"];
    [self populateText];
    // Do any additional setup after loading the view.
}

-(void)populateText{
    self.txtBio.text = @"Smoooooooooooke on the waaaater! NA NA NA NA NA NA NANA!";
    self.lblName.text = @"Tyrion Lannister";
}

@end
