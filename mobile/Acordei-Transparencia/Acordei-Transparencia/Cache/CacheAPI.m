#import <Foundation/Foundation.h>
#import "CacheAPI.h"


@implementation CacheAPI : NSObject

static CacheAPI *sharedDatabase = nil;

+ (CacheAPI *)sharedDatabase {
    if (sharedDatabase == nil) {
        sharedDatabase = [[super allocWithZone:NULL] init];
    }
    
    return sharedDatabase;
}

- (void)initDB {
    NSString *databaseName = @"Transparencia.db";
    NSArray *documentPaths = NSSearchPathForDirectoriesInDomains(NSDocumentDirectory, NSUserDomainMask, YES);
    NSString *documentDir = [documentPaths objectAtIndex:0];
    NSString *databasePath = [documentDir stringByAppendingPathComponent:databaseName];
    
    _db = [FMDatabase databaseWithPath:databasePath];
    
    if (![_db open])
        NSLog(@"Database problem");
}

- (FMDatabase *)getDB {
    return _db;
}

- (id)init {
    self = [super init];
    if (self) {
        // Work your initialising magic here as you normally would
    }
    return self;
}

- (id)copyWithZone:(NSZone *)zone {
    return self;
}

@end