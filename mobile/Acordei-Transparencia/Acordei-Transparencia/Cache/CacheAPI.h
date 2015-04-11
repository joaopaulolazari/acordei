#import "FMDB.h"
@interface CacheAPI : NSObject

    @property (strong, nonatomic) FMDatabase *db;

    + (id)sharedDatabase;
    - (void)initDB;
    - (FMDatabase *)getDB;
    -(void) putByKey:(NSString *)key json:(NSString *) json;
    -(NSString *) getByKey:(NSString * ) key;

@end