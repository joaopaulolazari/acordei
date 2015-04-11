@interface ApiCall : NSObject
    typedef void (^CompleteDiceRolling)(NSString *responseData);

     @property (copy, nonatomic) CompleteDiceRolling completeWithSuccess;
     @property (copy, nonatomic) CompleteDiceRolling completeWithError;

     -(void) callWithUrl:(NSString *) url SuccessCallback:(void (^)(NSString *responseData)) successBlock ErrorCallback:(void (^)(NSString *responseData)) erroBlock;
@end

