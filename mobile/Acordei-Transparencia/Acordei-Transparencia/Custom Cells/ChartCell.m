//
//  ChartCell.m
//  Acordei-Transparencia
//
//  Created by Giovane Possebon on 12/4/15.
//  Copyright (c) 2015 acordei. All rights reserved.
//

#import "ChartCell.h"
#import "LegendCell.h"

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
    [self.tableView setDataSource:self];
    [self.tableView setDelegate:self];
    [self.pieChart setDataSource:self];
    [self.pieChart setDelegate:self];
    [self.pieChart setLabelColor:[UIColor blueColor]];
    [self.pieChart setPieCenter:CGPointMake(80, 60)];
    [self.pieChart setPieRadius:60];
    
}

-(void)layoutSubviews
{
    [super layoutSubviews];
    self.imgPolitician.layer.borderWidth = 2;
    self.imgPolitician.layer.borderColor = [UIColor whiteColor].CGColor;
    self.imgPolitician.layer.cornerRadius = 8;
    self.imgPolitician.layer.masksToBounds = YES;
    [self.imgPolitician setClipsToBounds:YES];
    [self.imgPolitician setContentMode:UIViewContentModeScaleAspectFill];
    self.imgPolitician.layer.cornerRadius = roundf(self.imgPolitician.frame.size.width / 2.0);
}


-(NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section {
    return self.numberOfSlices;
}

-(UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath {
    LegendCell *cell = [self.tableView dequeueReusableCellWithIdentifier:@"cell4"];
    
    cell.lblTitle.text = [[self.slices valueForKey:@"eixoYLabels"] objectAtIndex:indexPath.row];
    cell.color.backgroundColor = [self.sliceColors objectAtIndex:(indexPath.row % self.sliceColors.count)];
    
    return cell;
    
}

-(NSUInteger)numberOfSlicesInPieChart:(XYPieChart *)pieChart {
    return self.numberOfSlices;
}

-(CGFloat)pieChart:(XYPieChart *)pieChart valueForSliceAtIndex:(NSUInteger)index {
    return [[[self.slices valueForKey:@"eixoYValores"] objectAtIndex:index] floatValue] ;
}

-(UIColor *)pieChart:(XYPieChart *)pieChart colorForSliceAtIndex:(NSUInteger)index {
    
    return [self.sliceColors objectAtIndex:(index % self.sliceColors.count)];
}

@end
