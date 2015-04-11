#import "FMDB.h"
@interface CacheAPI : NSObject

    @property (strong, nonatomic) FMDatabase *db;

    + (id)sharedDatabase;
    - (void)initDB;
    - (FMDatabase *)getDB;

@end