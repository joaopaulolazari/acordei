//
//  ProfilePhotoUtil.m
//  Acordei
//
//  Created by Giovane Berny Possebon on 15/07/14.
//  Copyright (c) 2014 Giovane Berny Possebon. All rights reserved.
//

#import "ProfilePhotoUtil.h"

@implementation ProfilePhotoUtil

-(UIImageView *)photoStyle:(UIImageView *)imgProfile andSize:(float)size{
    imgProfile.layer.borderWidth = 2;
    imgProfile.frame = CGRectMake(10, 10, size, size);
    imgProfile.layer.borderColor = [UIColor whiteColor].CGColor;
    imgProfile.layer.cornerRadius = CGRectGetHeight(imgProfile.frame)/2;
    imgProfile.clipsToBounds = YES;
    [imgProfile setContentMode:UIViewContentModeScaleAspectFill];
    
    
    return imgProfile;
}

@end
