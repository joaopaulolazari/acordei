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

@interface ViewController ()

@end

@implementation ViewController

- (void)viewDidLoad {
    [super viewDidLoad];

    [[[ApiCall alloc] init]
        callWithUrl:@"https://www.kimonolabs.com/api/28d5kkdo?apikey=10deb955005b151ee7f6d2d2c796cde6"
        SuccessCallback:^(NSString *message){
            NSLog(@"Veio do WS essa mensagem de successo: %@",message);
        }ErrorCallback:^(NSString *erro){
            NSLog(@"Veio do WS essa mensagem de erro: %@",erro);
        }];

//    [[CacheAPI sharedDatabase] putByKey:@"deivison" json:@"{id:1}"];
    NSLog(@"From database by deivison => %@",[[CacheAPI sharedDatabase] getByKey:@"deivison"]);
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

@end
