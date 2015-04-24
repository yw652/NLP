import sys
import os


def feature_1():
    '''
    Baseline: 2 features
    a. current
    b. pos tag
    '''
    f = open('train.txt', 'r')
    o = open('event1', 'w')
    for line in f:
        try:
            listFeature = line.split(' ')
            feature = []
            feature.append('current=' + listFeature[0])
            feature.append('pos=' + listFeature[1])
            tag = listFeature[2].strip('\n')
            o.write(str(feature[0]) + ' ' + str(feature[1]) + ' ' + tag + '\n')
        except:
            o.write('\n')
            pass
    o.close()
def feature_2():
    '''
    1 extra feature: 
    --- 'initCapitalized': If the word is capitalized or not
    '''
    f = open('train.txt', 'r')
    o = open('event2', 'w')
    for line in f:
        try:
            listFeature = line.split(' ')
            o.write('current=' + listFeature[0])
            o.write(' pos=' + listFeature[1])
            o.write(' initCapitalized=' + str(feature[0][0].isupper()) + ' ' + listFeature[2])
        except:
            o.write('\n')
            pass
    o.close()


def feature_3():
    '''
    # 2 extra features:
    # --- 'prev' and 'prevPOS'
    '''
    f = open('train.txt','r')
    o = open('event3', 'w')
    prevLine = []
    output = ''
    for line in f:
        if (len(line) > 0):
            try:
                strs = line.split(' ')
                if (len(prevLine) > 0):
                    output = output[:(len(output)-2)] + 'next=' + strs[0] + ' nextPOS=' + strs[1] + \
                            ' ' + output[len(output)-2:]
                    o.write(output)
                    output = 'prev=' + prevLine[0] + ' prevPOS=' + prevLine[1] + ' current=' + \
                        strs[0] + ' pos=' + strs[1] + ' initCapitalized=' + str(strs[0][0].isupper()) + \
                        ' ' + strs[2]
                else:
                    output = 'prev=null prevPOS=null' + ' current=' + strs[0] + ' pos=' + strs[1] + \
                        ' initCapitalized=' + str(strs[0][0].isupper()) + ' ' + strs[2]
                prevLine = strs
            except:
                # o.write('\n')
                pass
        else:
            output = output[:(len(output)-2)] + 'next=null nextPOS=null ' + output[len(output)-2:]
            o.write(output)
            prevLine = None
    o.close()

def feature_4():
    '''
    # 1 extra feature:
    # --- 'isSuffixIng': which indicates whether the word has a suffix 'ing' tagged in the end
    '''
    f = open('train.txt', 'r')
    o = open('event4', 'w')
    prevLine = []
    output = ''
    for line in f:
        if (len(line) > 0):
            try:
                strs = line.split(' ')
                if (len(prevLine) > 0):
                    output = output[:(len(output)-2)] + 'next=' + strs[0] + ' nextPOS=' + strs[1] + \
                            ' ' + output[len(output)-2:]
                    o.write(output)
                    output = 'prev=' + prevLine[0] + ' prevPOS=' + prevLine[1] + ' current=' + \
                        strs[0] + ' pos=' + strs[1] + ' initCapitalized=' + str(strs[0][0].isupper())
                else:
                    output = 'prev=null prevPOS=null' + ' current=' + strs[0] + ' pos=' + strs[1] + \
                        ' initCapitalized=' + str(strs[0][0].isupper())
                # Add the suffix-ing feature
                if (len(strs[0]) >= 3):
                    output += ' isSuffixIng=' + str(strs[0][-3:] == 'ing')
                else:
                    output += ' isSuffixIng=False'
                output += ' ' + strs[2]
                prevLine = strs
            except:
                pass
        else:
            output = output[:len(output) - 2] + 'next=null nextPOS=null ' + output[len(output) - 2:] 
            o.write(output)
            prevLine = None 
    o.close() 

if __name__ == '__main__':
    feature_1()   
    feature_2() 
    feature_3() 
    feature_4()                               
