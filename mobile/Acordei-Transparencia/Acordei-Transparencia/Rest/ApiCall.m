//
//  www.example.m
//  Acordei-Transparencia
//
//  Created by Deivison Sporteman on 4/11/15.
//  Copyright (c) 2015 acordei. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "ApiCall.h"


@implementation ApiCall : NSObject

- (void) callWithUrl:(NSString *) url SuccessCallback:(void (^)(NSString *responseData)) successBlock ErrorCallback:(void (^)(NSString *responseData)) erroBlock {
    
    self.completeWithSuccess = successBlock;
    self.completeWithError = erroBlock;

    NSURL *myURL = [NSURL URLWithString:url];
    NSMutableURLRequest *request = [NSMutableURLRequest requestWithURL:myURL cachePolicy:NSURLRequestReloadIgnoringLocalCacheData timeoutInterval:60];
    
    NSOperationQueue *queue = [[NSOperationQueue alloc]init];
    [NSURLConnection sendAsynchronousRequest:request
                                       queue:queue
                           completionHandler:^(NSURLResponse *response, NSData *data, NSError *error){
                               if (error) {
                                   self.completeWithError(error.localizedDescription);
                               }
                               self.completeWithSuccess([[NSString alloc] initWithData:data encoding:NSUTF8StringEncoding]);
                           }];
}

@end