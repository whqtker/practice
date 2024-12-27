import time
import pandas as pd
import random
from itertools import combinations

from pyspark.sql import SparkSession

# 예제 데이터 생성
names = [f'Name{i}' for i in range(1, 101)]
mbti_types = ['INTJ', 'ENTP', 'INFJ', 'ENFP', 'ISTJ', 'ESFP', 
              'ISFJ', 'ESTP', 'INTP', 'ENTJ', 'ISTP', 'ENFJ']

data = {
    'Name': names,
    'MBTI': [random.choice(mbti_types) for _ in range(100)]
}

df = pd.DataFrame(data)

def using_pyspark():
    spark = SparkSession.builder.appName("Combinations").getOrCreate()
    sdf = spark.createDataFrame(df.to_dict(orient='records'))

    start_time = time.time()
    comb = list(combinations(range(sdf.count()), 2))

    collected_sdf = sdf.collect()

    # 각 조합에 대해 DataFrame 생성 후 합치기
    sampled_dfs = []
    for c in comb:
        sampled_dfs.append({
            'Name': [collected_sdf[i]['Name'] for i in c],
            'MBTI': [collected_sdf[i]['MBTI'] for i in c]
        })

    result_df = spark.createDataFrame(sampled_dfs)
    print(f'pyspark 소요 시간: {time.time() - start_time:.2f}s')
    result_df.show()

def using_pandas():
    start_time = time.time()
    comb = list(combinations(df.index, 2))

    # 각 조합에 대해 DataFrame 생성 후 합치기
    sampled_dfs = pd.DataFrame([{
        'Name': [df.loc[i, 'Name'] for i in c],
        'MBTI': [df.loc[i, 'MBTI'] for i in c]
    } for c in comb])

    print(f'pandas 소요 시간: {time.time() - start_time:.2f}s')
    print(sampled_dfs)

if __name__ == '__main__':
    using_pyspark()
    using_pandas()