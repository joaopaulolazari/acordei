//
//  ChartCell.m
//  Acordei-Transparencia
//
//  Created by Giovane Possebon on 12/4/15.
//  Copyright (c) 2015 acordei. All rights reserved.
//

#import "ChartCell.h"

@implementation ChartCell

- (void)awakeFromNib {
    [super awakeFromNib];
    
    self.sliceColors =[NSArray arrayWithObjects:
                       [UIColor colorWithRed:246/255.0 green:155/255.0 blue:0/255.0 alpha:1],
                       [UIColor colorWithRed:129/255.0 green:195/255.0 blue:29/255.0 alpha:1],
                       [UIColor colorWithRed:62/255.0 green:173/255.0 blue:219/255.0 alpha:1],
                       [UIColor colorWithRed:229/255.0 green:66/255.0 blue:115/255.0 alpha:1],
                       [UIColor colorWithRed:148/255.0 green:141/255.0 blue:139/255.0 alpha:1],nil];
    
    // Initialization code
    [self.pieChart setDataSource:self];
    [self.pieChart setDelegate:self];
    
    //Note: the actual pie slices are set inside the cells
    [self.pieChart reloadData];
    
}

-(NSUInteger)numberOfSlicesInPieChart:(XYPieChart *)pieChart {
    return self.numberOfSlices;
}

-(CGFloat)pieChart:(XYPieChart *)pieChart valueForSliceAtIndex:(NSUInteger)index {
    return [[[[self.slices objectAtIndex:index]valueForKey:@"eixoYValores"]objectAtIndex:index]floatValue];
}

-(UIColor *)pieChart:(XYPieChart *)pieChart colorForSliceAtIndex:(NSUInteger)index {
    return [self.sliceColors objectAtIndex:(index % self.sliceColors.count)];
}

@end
