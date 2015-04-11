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

-(void) putByKey:(NSString *)key json:(NSString *) json{
    [_db executeUpdate:@"INSERT INTO dashboard(key,json_value) VALUES (?,?)", key,json];
}

-(NSString *) getByKey:(NSString * ) key {
    FMResultSet *s = [_db executeQuery:@"SELECT json_value FROM dashboard where key=?" withArgumentsInArray:[[NSArray alloc] initWithObjects:key, nil ]];
    if ([s next]) {
        return [s stringForColumn:@"json_value"];
    }
    return NULL;
}

- (id)init {
    self = [super init];
    if (self) {
        [self initDB];
        [self createTables];
    }
    return self;
}

-(void) createTables {
    NSString *sql = @"create table IF NOT EXISTS dashboard (id integer primary key autoincrement, key text, json_value text,UNIQUE(key) ON CONFLICT REPLACE);";
    [_db executeStatements:sql];
}

- (id)copyWithZone:(NSZone *)zone {
    return self;
}

@end