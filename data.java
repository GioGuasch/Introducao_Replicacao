import numpy as np
from collections import defaultdict
from scipy.stats import weibull_min, lognorm
import matplotlib.pyplot as plt

CACHE_SIZE = 100 
NUM_REQUESTS = 10000 
NUM_OBJECTS = 1000 

def generate_weibull_data(shape, scale, size):
    return weibull_min.rvs(c=shape, scale=scale, size=size)

def generate_lognormal_data(mean, sigma, size):
    return lognorm.rvs(sigma, scale=np.exp(mean), size=size)

def generate_uniform_data(size):
    return np.random.uniform(0, 1, size)

class LFUCache:
    def _init_(self, capacity):
        self.capacity = capacity
        self.cache = {}
        self.freq = defaultdict(int)

    def access(self, obj):
        if obj in self.cache:
            self.freq[obj] += 1
            return True  # Cache hit
        else:
            if len(self.cache) >= self.capacity:
                # Remover o objeto menos frequentemente usado
                lfu_obj = min(self.cache, key=lambda k: self.freq[k])
                del self.cache[lfu_obj]
                del self.freq[lfu_obj]
            self.cache[obj] = True
            self.freq[obj] = 1
            return False  # Cache miss

def simulate_requests(popularity_data, cache):
    hits = 0
    total_requests = len(popularity_data)
    
    for obj in popularity_data:
        if cache.access(obj):
            hits += 1
    
    hit_rate = hits / total_requests
    return hit_rate

def plot_results(results):
    policies = list(results.keys())
    hit_rates = list(results.values())

    plt.bar(policies, hit_rates, color=['blue', 'green', 'orange'])
    plt.xlabel('Política de Substituição')
    plt.ylabel('Taxa de Acertos (%)')
    plt.title('Comparação de Políticas de Cache')
    plt.show()

def main():
    weibull_popularity = generate_weibull_data(shape=1.5, scale=1.0, size=NUM_REQUESTS)
    lognormal_popularity = generate_lognormal_data(mean=0, sigma=1.0, size=NUM_REQUESTS)
    uniform_popularity = generate_uniform_data(NUM_REQUESTS)
    
    weibull_popularity = np.floor(weibull_popularity * NUM_OBJECTS / max(weibull_popularity)).astype(int)
    lognormal_popularity = np.floor(lognormal_popularity * NUM_OBJECTS / max(lognormal_popularity)).astype(int)
    uniform_popularity = np.floor(uniform_popularity * NUM_OBJECTS / max(uniform_popularity)).astype(int)

    lfu_cache_weibull = LFUCache(CACHE_SIZE)
    lfu_cache_lognormal = LFUCache(CACHE_SIZE)
    lfu_cache_uniform = LFUCache(CACHE_SIZE)

    hit_rate_weibull = simulate_requests(weibull_popularity, lfu_cache_weibull)
    hit_rate_lognormal = simulate_requests(lognormal_popularity, lfu_cache_lognormal)
    hit_rate_uniform = simulate_requests(uniform_popularity, lfu_cache_uniform)

    results = {
        "Weibull (LFU)": hit_rate_weibull,
        "Log-Normal (LFU)": hit_rate_lognormal,
        "Uniform (LFU)": hit_rate_uniform
    }
    
    print("Taxas de acerto no cache:")
    for policy, hit_rate in results.items():
        print(f"{policy}: {hit_rate * 100:.2f}%")

    plot_results(results)

if _name_ == "_main_":
    main()
