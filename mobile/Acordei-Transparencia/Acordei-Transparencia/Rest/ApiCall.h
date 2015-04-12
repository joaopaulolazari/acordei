@interface ApiCall : NSObject
    typedef void (^CompleteDiceRolling)(NSData *responseData);

     @property (copy, nonatomic) CompleteDiceRolling completeWithSuccess;
     @property (copy, nonatomic) CompleteDiceRolling completeWithError;

     -(void) callWithUrl:(NSString *) url SuccessCallback:(void (^)(NSData *responseData)) successBlock ErrorCallback:(void (^)(NSData *responseData)) erroBlock;
@end

