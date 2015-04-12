//
//  DashboardCell.m
//  Acordei-Transparencia
//
//  Created by Giovane Possebon on 11/4/15.
//  Copyright (c) 2015 acordei. All rights reserved.
//

#import "DashboardCell.h"

@implementation DashboardCell

-(void)layoutSubviews
{
    [super layoutSubviews];
    self.imgProfile.layer.borderWidth = 2;
    self.imgProfile.layer.borderColor = [UIColor whiteColor].CGColor;
    self.imgProfile.layer.cornerRadius = 8;
    self.imgProfile.layer.masksToBounds = YES;
    [self.imgProfile setClipsToBounds:YES];
    [self.imgProfile setContentMode:UIViewContentModeScaleAspectFill];
    self.imgProfile.layer.cornerRadius = roundf(self.imgProfile.frame.size.width / 2.0);
}

@end
