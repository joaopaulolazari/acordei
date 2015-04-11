//
//  PoliticsCustomCell.m
//  Acordei-Transparencia
//
//  Created by Giovane Possebon on 11/4/15.
//  Copyright (c) 2015 acordei. All rights reserved.
//

#import "PoliticsCustomCell.h"

@implementation PoliticsCustomCell

-(void)layoutSubviews
{
    [super layoutSubviews];
    self.imgPerson.layer.borderWidth = 2;
    self.imgPerson.layer.borderColor = [UIColor whiteColor].CGColor;
    self.imgPerson.layer.cornerRadius = 8;
    self.imgPerson.layer.masksToBounds = YES;
    [self.imgPerson setClipsToBounds:YES];
    [self.imgPerson setContentMode:UIViewContentModeScaleAspectFill];
    self.imgPerson.layer.cornerRadius = roundf(self.imgPerson.frame.size.width / 2.0);
}

@end
