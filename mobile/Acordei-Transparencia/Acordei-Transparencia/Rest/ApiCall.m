#import <Foundation/Foundation.h>
#import "ApiCall.h"


@implementation ApiCall : NSObject

- (void) callWithUrl:(NSString *) url SuccessCallback:(void (^)(NSData *responseData)) successBlock ErrorCallback:(void (^)(NSData *responseData)) erroBlock {
    
    self.completeWithSuccess = successBlock;
    self.completeWithError = erroBlock;

    NSURL *myURL = [NSURL URLWithString:url];
    NSMutableURLRequest *request = [NSMutableURLRequest requestWithURL:myURL cachePolicy:NSURLRequestReloadIgnoringLocalCacheData timeoutInterval:60];
    
    NSOperationQueue *queue = [[NSOperationQueue alloc]init];
    [NSURLConnection sendAsynchronousRequest:request
                                       queue:queue
                           completionHandler:^(NSURLResponse *response, NSData *data, NSError *error){
                               if (error) {
                                   self.completeWithError(nil);
                               }
                               self.completeWithSuccess(data);
                           }];
}

@end