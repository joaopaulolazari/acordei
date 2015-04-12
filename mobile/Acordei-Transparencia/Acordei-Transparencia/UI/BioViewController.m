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

@interface BioViewController ()

@end

@implementation BioViewController
{
    NSArray *biografia;
}

- (void)viewDidLoad {
    [super viewDidLoad];
    NSString *nomeParlamentar = [self.fromArray valueForKey:@"nomeParlamentar"];
    dispatch_async(dispatch_get_global_queue( DISPATCH_QUEUE_PRIORITY_LOW, 0), ^{
        [[[ApiCall alloc] init] callWithUrl:[NSString stringWithFormat:@"https://www.kimonolabs.com/api/json/ondemand/bx2r958a?apikey=10deb955005b151ee7f6d2d2c796cde6&kimpath1=%@", nomeParlamentar]
                            SuccessCallback:^(NSData *message){
                                biografia = [self populatePoliticiansArray:message];
                                NSLog(@"%@", biografia);
                                dispatch_async(dispatch_get_main_queue(), ^{
                                    [self populateText];
                                });
                            }ErrorCallback:^(NSData *erro){
                                NSLog(@"Veio do WS essa mensagem de erro: %@",erro);
                            }];
    });
    // Do any additional setup after loading the view.
}

-(NSArray *)populatePoliticiansArray:(NSData *)data {
    NSError *error;
    return [NSJSONSerialization JSONObjectWithData:data options:kNilOptions error:&error];
}


-(void)populateText{
    self.txtBio.text = @"Smoooooooooooke on the waaaater! NA NA NA NA NA NA NANA!";
    self.lblName.text = [self.fromArray valueForKey:@"nome"];
}

@end
